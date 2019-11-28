/*
 * Copyright 2006-2019 (c) Care.com, Inc.
 * 1400 Main Street, Waltham, MA, 02451, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Care.com, Inc. ("Confidential Information").  You shall not disclose
 * such Confidential Information and shall use it only in accordance with
 * the terms of an agreement between you and CZen.
 */
package com.SystemDesign.URLShortner;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * Created 9/10/2019 2:25 AM
 *
 * @author Rohit Rawani
 */

@RestController
@RequestMapping("/shortUrl")
public class URLController {

  @Autowired
  StringRedisTemplate template;

  @GetMapping("/{id}")
  public String getURL(@PathVariable String id){
    return template.opsForValue().get(id);
  }

  @PostMapping()
  public String createURL(@RequestBody String url) {
    if (!StringUtils.isEmpty(url)) {
      String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
      template.opsForValue().set(id, url);
      System.out.println("Hashed id for the url : " + id);
      return id;
    }else{
      throw new RuntimeException("Not a valid URL");
    }
  }
}
