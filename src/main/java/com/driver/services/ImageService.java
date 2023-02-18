package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;
    @Autowired
    private UserRepository userRepository;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog
        Image image = new Image();
        image.setDescription(description);
        image.setDimensions(dimensions);
        Blog blog = blogRepository2.findById(blogId).get();
        List<Image> imageList = blog.getImageList();
        imageList.add(image);
        blog.setImageList(imageList);
        blogRepository2.save(blog);
        return image;
    }

    public void deleteImage(Integer id){
        imageRepository2.deleteById(id);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        Image image = imageRepository2.findById(id).get();
        String imageDimension = image.getDimensions();

        String  screenArr[] = screenDimensions.split("X");
        int screenLength = Integer.parseInt(screenArr[0]);
        int screenBreadth = Integer.parseInt(screenArr[1]);

        String imageArr[] = imageDimension.split("X");
        int imageLength = Integer.parseInt(imageArr[0]);
        int imageBreadth = Integer.parseInt(imageArr[1]);

        int lengthCount = screenLength/imageLength;
        int breadthCount = screenBreadth/imageBreadth;

        return lengthCount*breadthCount;
    }
}
