package com.stackroute.test.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.stackroute.domain.Blog;
import com.stackroute.exception.BlogAlreadyExistsException;
import com.stackroute.exception.BlogNotFoundException;
import com.stackroute.repository.BlogRepository;
import com.stackroute.service.BlogServiceImpl;


@SpringBootTest
class BlogRepositoryIntegrationTest {

    @MockBean
    private BlogRepository blogRepository;
    private Blog blog;
    private List<Blog> blogList;

    
    @Autowired
    BlogServiceImpl blogService;
    
    @BeforeEach
    public void setUp() {
        blog = new Blog();
        blog.setBlogId(1);
        blog.setBlogTitle("Blog1");
        blog.setAuthorName("Imneet");
        blog.setBlogContent("Sample content");
        blogList = new ArrayList<>();
        blogList.add(blog);
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
    	when(blogRepository.save(any(Blog.class))).thenReturn(blog);
    	assertEquals(blog,blogService.saveBlog(blog));
    	verify(blogRepository).save(blog);
    }
    
    @Test
    public void givenBlogToSaveThenShouldReturnExist() {
    	when(blogRepository.existsById(1)).thenThrow(new BlogAlreadyExistsException("Blog with ID 1 already exists."));
    	BlogAlreadyExistsException exception = assertThrows(BlogAlreadyExistsException.class, () -> {blogService.saveBlog(blog);});
    	assertEquals("Blog with ID 1 already exists.", exception.getMessage());
    	verify(blogRepository).existsById(1);
    }


    /*
    * Test for getting all blogs: blogRepository.findAll() method
     */
    @Test
    public void givenGetAllBlogsThenShouldReturnListOfAllBlogs() {
    	when(blogRepository.findAll()).thenReturn(blogList);
    	assertEquals(blogList,blogService.getAllBlogs());
    	verify(blogRepository).findAll();
    }

    /*
    * Test for getting a blog by id: blogRepository.findById(blogId) method
     */
    @Test
    public void givenBlogIdThenShouldReturnRespectiveBlog() {
    	when(blogRepository.findById(1)).thenReturn(Optional.of(blog));
    	assertEquals(blog,blogService.getBlogById(1));
    	Mockito.verify(blogRepository).findById(1);
    	
    }
    
    
    @Test
    public void givenBlogIdThenShouldReturnException() {
    	when(blogRepository.findById(1)).thenReturn(Optional.ofNullable(null));
    	BlogNotFoundException exception = assertThrows(BlogNotFoundException.class, () -> {blogService.getBlogById(1);});
    	assertEquals("Blog not found with ID 1", exception.getMessage());
    	verify(blogRepository).findById(1);
    	
    }

    /*
    * Test for deleting a blog by id: blogRepository.deleteById(blogId) method
     */
    @Test
    public void givenBlogIdToDeleteThenShouldReturnDeletedBlog() {
    	when(blogRepository.findById(1)).thenReturn(Optional.of(blog));
    	doNothing().when(blogRepository).deleteById(1);
    	assertEquals(blog,blogService.deleteBlog(1));
    	verify(blogRepository).findById(1);
    	verify(blogRepository).deleteById(1);
    }
    
    @Test
    public void givenBlogIdToDeleteThenShouldReturnException() {
    	when(blogRepository.findById(1)).thenReturn(Optional.ofNullable(null));
    	BlogNotFoundException exception = assertThrows(BlogNotFoundException.class, () -> {blogService.deleteBlog(1);});
    	assertEquals("Blog not found with ID 1", exception.getMessage());
    	verify(blogRepository).findById(1);
    }
    
    
    @Test
    public void testUpdateBlog() {
    	when(blogRepository.findById(1)).thenReturn(Optional.of(blog));
    	when(blogRepository.save(blog)).thenReturn(blog);
    	assertEquals(blog,blogService.updateBlog(blog));
    	verify(blogRepository).findById(1);
    	verify(blogRepository).save(blog);
    }
    
    
    @Test
    public void testUpdateBlogExcetion() {
    	when(blogRepository.findById(1)).thenReturn(Optional.ofNullable(null));
    	BlogNotFoundException exception = assertThrows(BlogNotFoundException.class, () -> {blogService.updateBlog(blog);});
    	assertEquals("Blog not found with ID 1", exception.getMessage());
    	verify(blogRepository).findById(1);
    }

}