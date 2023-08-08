package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.exceptions.BadApiRequest;
import com.lcwd.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {
        //abc.png
        String originalFilename = file.getOriginalFilename();
        logger.info("FileName : {}", originalFilename);
        String fileName = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = fileName + extension;
        String fullPathWithFileName = path + File.separator + fileNameWithExtension;

        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") ||
        extension.equalsIgnoreCase(".jpeg")){

            //Creating folder
            File folder = new File(path);

            if(!folder.exists()){
                //Create Folder, mkdirs -> creating many levels of folder
                folder.mkdirs();
            }

            //file upload
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;

        }else{
            throw new BadApiRequest("File with this" + extension + "not allowed");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path + File.separator + name;

        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
