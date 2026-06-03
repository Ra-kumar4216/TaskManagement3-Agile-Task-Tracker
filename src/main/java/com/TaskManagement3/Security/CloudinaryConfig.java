package com.TaskManagement3.Security;

import java.util.HashMap;
import java.util.Map;

import com.cloudinary.Cloudinary;

public class CloudinaryConfig {

    private String cloudName = System.getenv("CLOUDINARY_CLOUD_NAME");
    private String apiKey = System.getenv("CLOUDINARY_API_KEY");
    private String apiSecret = System.getenv("CLOUDINARY_API_SECRET");

    public Cloudinary cloudinary() {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        
        return new Cloudinary(config);
    }
}