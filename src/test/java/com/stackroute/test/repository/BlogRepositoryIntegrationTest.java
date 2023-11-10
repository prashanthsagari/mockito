package com.stackroute.test.repository;

import com.stackroute.domain.Blog;
import com.stackroute.repository.BlogRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BlogRepositoryIntegrationTest {

    @Autowired
    private BlogRepository blogRepository;
    private Blog blog;

    @BeforeEach
    public void setUp() {
        blog = new Blog();
        blog.setBlogId(1);
        blog.setBlogTitle("Blog1");
        blog.setAuthorName("Imneet");
        blog.setBlogContent("Sample content");
    }
    @AfterEach
    public void tearDown() {
        blogRepository.deleteAll();
        blog = null;
    }

    /*
    * Test for saving a blog in database: blogRepository.save(blog) method
     */
    @Test
    public void givenBlogToSaveThenShouldReturnSavedBlog() {
    }


    /*
    * Test for getting all blogs: blogRepository.findAll() method
     */
    @Test
    public void givenGetAllBlogsThenShouldReturnListOfAllBlogs() {
    }

    /*
    * Test for getting a blog by id: blogRepository.findById(blogId) method
     */
    @Test
    public void givenBlogIdThenShouldReturnRespectiveBlog() {
    }

    /*
    * Test for deleting a blog by id: blogRepository.deleteById(blogId) method
     */
    @Test
    public void givenBlogIdToDeleteThenShouldReturnDeletedBlog() {
    }

}