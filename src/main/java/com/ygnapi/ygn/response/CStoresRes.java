package com.ygnapi.ygn.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CStoresRes {


    private Integer id;

    private String userId;

    private String brand;

    private Integer price;

    private String description;

    private String imageName;

   // private byte[] image;
    private URL imageUrl;
}
