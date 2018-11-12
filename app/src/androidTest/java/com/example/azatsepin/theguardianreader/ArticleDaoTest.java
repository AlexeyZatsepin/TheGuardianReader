package com.example.azatsepin.theguardianreader;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.azatsepin.theguardianreader.domain.Article;
import com.example.azatsepin.theguardianreader.datasource.AppDatabase;
import com.example.azatsepin.theguardianreader.datasource.ArticleDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ArticleDaoTest {

    private AppDatabase db;
    private ArticleDao articleDao;

    @Before
    public void createDb() {
        db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase.class)
                .build();
        articleDao = db.articleDao();
    }

    private List<Article> createListOfArticle(int n) {
        List<Article> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Article article = new Article();
            article.setUrl("test" + i);
            result.add(article);
        }
        return result;
    }

    @Test
    public void whenInsertArticleThenReadTheSameOne() {
        List<Article> articles = createListOfArticle(1);

        articleDao.insert(articles.get(0));
        List<Article> dbArticles = articleDao.getAll();

        assertEquals(1, dbArticles.size());
        assertEquals(articles.get(0), dbArticles.get(0));
    }

    @Test
    public void whenUpdateArticleThenReadTheSameOne() {
        List<Article> articles = createListOfArticle(1);
        Article article = articles.get(0);
        articleDao.insert(article);

        article.setUrl(article.getUrl()+"test");
        articleDao.update(article);

        List<Article> dbArticles = articleDao.getAll();
        assertNotEquals(articles.get(0), dbArticles.get(0));
    }

    @Test
    public void whenInsertArticlesThenReadThem() {
        List<Article> articles = createListOfArticle(5);
        articles.forEach(it -> articleDao.insert(it));
        assertEquals(5, articleDao.getAll().size());
    }

    @Test
    public void whenDeleteAllThenReadNothing() {
        List<Article> articles = createListOfArticle(5);
        articles.forEach(it -> it.setId(articleDao.insert(it)));
        articles.forEach(it -> articleDao.delete(it));
        assertTrue(articleDao.getAll().isEmpty());
    }

    @After
    public void closeDb() {
        db.close();
    }

}