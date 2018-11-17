package com.example.azatsepin.theguardianreader.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.azatsepin.theguardianreader.domain.Article;
import com.example.azatsepin.theguardianreader.datasource.AppDatabase;
import com.example.azatsepin.theguardianreader.datasource.ArticleDao;
import com.example.azatsepin.theguardianreader.domain.ArticleEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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

    private List<ArticleEntity> createListOfArticle(int n) {
        List<ArticleEntity> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArticleEntity article = new ArticleEntity();
            article.setBody("test" + i);
            result.add(article);
        }
        return result;
    }

    @Test
    public void whenInsertArticleThenReadTheSameOne() throws InterruptedException {
        List<ArticleEntity> articles = createListOfArticle(1);

        articleDao.insert(articles.get(0));
        LiveData<List<ArticleEntity>> dbArticles = articleDao.getAll();

        final CountDownLatch latch = new CountDownLatch(1);

        dbArticles.observeForever(articles1 -> {
            assertEquals(1, articles1.size());
            assertEquals(articles1.get(0), articles1.get(0));
            latch.countDown();
        });
        latch.await();


    }

    @Test
    public void whenUpdateArticleThenReadTheSameOne() throws InterruptedException {
        List<ArticleEntity> articles = createListOfArticle(1);
        ArticleEntity article = articles.get(0);
        articleDao.insert(article);

        article.setBody(article.getBody()+"test");
        articleDao.update(article);

        LiveData<List<ArticleEntity>> dbArticles = articleDao.getAll();
        final CountDownLatch latch = new CountDownLatch(1);
        dbArticles.observeForever(a -> {
            assertNotEquals(articles.get(0), a.get(0));
            latch.countDown();
        });

        latch.await();

    }

    @Test
    public void whenInsertArticlesThenReadThem() throws InterruptedException {
        List<ArticleEntity> articles = createListOfArticle(5);
        articles.forEach(it -> articleDao.insert(it));
        final CountDownLatch latch = new CountDownLatch(1);
        articleDao.getAll().observeForever(a -> {
            assertEquals(5, a.size());
            latch.countDown();
        });
        latch.await();

    }

    @Test
    public void whenDeleteAllThenReadNothing() throws InterruptedException {
        List<ArticleEntity> articles = createListOfArticle(5);
        articles.forEach(it -> articleDao.insert(it));
        articles.forEach(it -> articleDao.delete(it));
        final CountDownLatch latch = new CountDownLatch(1);
        articleDao.getAll().observeForever(a -> {
            assertTrue(a.isEmpty());
            latch.countDown();
        });
        latch.await();
    }

    @After
    public void closeDb() {
        db.close();
    }

}