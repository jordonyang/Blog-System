package com.yang.www.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.yang.www.po.Blog;
import com.yang.www.utils.DateUtil;
import com.yang.www.utils.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class BlogIndexer {

    private Directory dir;

    /**
     * 获取写入索引文件书写器
     * @return
     */
    private IndexWriter getWriter() {
        IndexWriter writer = null;
        try {
            dir = FSDirectory.open(Paths.get("C://lucene"));
            SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            writer = new IndexWriter(dir, iwc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer;
    }

    /**
     * 将Blog属性写入索引
     * @param blog
     */
    public void addIndex(Blog blog) {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("blogId", String.valueOf(blog.getBlogId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        doc.add(new StringField("releaseDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
        doc.add(new TextField("content", blog.getContentWithoutTags(), Field.Store.YES));
        try {
            writer.addDocument(doc);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用lucene对博客标题和内容进行全文检索
     * @param input
     * @return
     * @throws Exception
     */
    public List<Blog> searchBlog(String input) {
        List<Blog> blogList = new ArrayList<>();
        try {
            dir = FSDirectory.open(Paths.get("C://lucene"));
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher is = new IndexSearcher(reader);
            BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
            SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();

            QueryParser titleParser = new QueryParser("title", analyzer);
            Query query = titleParser.parse(input);

            QueryParser contentParser = new QueryParser("content", analyzer);
            Query queryContent = contentParser.parse(input);

            booleanQuery.add(query, BooleanClause.Occur.SHOULD);
            booleanQuery.add(queryContent, BooleanClause.Occur.SHOULD);

            TopDocs hits = is.search(booleanQuery.build(), 100);
            QueryScorer scorer = new QueryScorer(query);

            Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
            SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
            Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
            highlighter.setTextFragmenter(fragmenter);

            blogList = new LinkedList<>();
            for (ScoreDoc scoreDoc : hits.scoreDocs) {
                Document doc = is.doc(scoreDoc.doc);
                Blog blog = new Blog();
                blog.setBlogId(Integer.parseInt(doc.get("blogId")));
                blog.setReleaseDateStr(doc.get("releaseDate"));
                //用apache.commons.lang组件对标签进行转义过滤
                String title =  StringEscapeUtils.escapeHtml(doc.get("title"));
                String content = StringEscapeUtils.escapeHtml(doc.get("content"));
                if (title != null) {
                    //获取最佳片段
                    TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
                    String bestTitle = highlighter.getBestFragment(tokenStream, title);
                    //如果搜索不到结果就设置原标题
                    if (StringUtil.isEmpty(bestTitle)) {
                        blog.setTitle(title);
                    } else {
                        blog.setTitle(bestTitle);
                    }
                }

                if (content != null) {
                    TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(content));
                    String bestContent = highlighter.getBestFragment(tokenStream, content);
                    //如果搜不到内容就将原在内容中截取前200个字符
                    if (StringUtil.isEmpty(bestContent)) {
                        if (content.length() <= 200) {
                            blog.setContent(content);
                        } else {
                            blog.setContent(content.substring(0, 200));
                        }
                    } else {
                        blog.setContent(bestContent);
                    }
                }
                blogList.add(blog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blogList;
    }

    /**
     * 删除blogId对应的索引
     * @param blogId
     */
    public void deleteIndex(String blogId) {
        IndexWriter writer = getWriter();
        try {
            writer.deleteDocuments(new Term("blogId", blogId));
            writer.forceMergeDeletes(); // 强制删除
            writer.commit();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新索引
     * @param blog
     */
    public void updateIndex(Blog blog) {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("blogId", String.valueOf(blog.getBlogId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        doc.add(new StringField("releaseDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
        doc.add(new TextField("content", blog.getContentWithoutTags(), Field.Store.YES));
        try {
            writer.updateDocument(new Term("blogId", String.valueOf(blog.getBlogId())), doc);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}