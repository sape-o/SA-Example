package com.sut.sa.cpe.controller;


import com.sut.sa.cpe.entity.Playlist;
import com.sut.sa.cpe.entity.User;
import com.sut.sa.cpe.entity.Video;

import com.sut.sa.cpe.repository.PlaylistRepository;
import com.sut.sa.cpe.repository.UserRepository;
import com.sut.sa.cpe.repository.VideoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PlaylistController {

    @Autowired  private   final    PlaylistRepository playlistRepository;
    @Autowired  private       UserRepository     userRepository;
    @Autowired  private       VideoRepository    videoRepository;
    

    PlaylistController(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }


    @GetMapping("/Playlists")
     public Collection<Playlist> Playlists() {
         return playlistRepository.findAll().stream()
                 .collect(Collectors.toList());
     }

    // ทดสอบโดย ใช้คำสั่ง curl -iX POST  http://localhost:8080/Playlsit/new/Sitthichai/testPlaylist

     @PostMapping("/Playlsit/new/{adder}/{namePlaylist}")
     public Playlist newPlaylsit(@PathVariable String namePlaylist,@PathVariable String adder) {

        Playlist newPlaylist = new Playlist();
        User userPlaylsit = userRepository.findByUsername(adder);
        newPlaylist.setAdder(userPlaylsit);
        newPlaylist.setName(namePlaylist);
        

         return  playlistRepository.save(newPlaylist); // บันทึก Objcet ชื่อ Playlist
     }

    // เพิ่ม Playlist ก่อน
    // ทดสอบโดย ใช้คำสั่ง curl -iX POST  http://localhost:8080/Playlsit/addVideo/2/dXi2FDWnySU,O8etN-2fc1c,r4j6H-f9j8Y 

     @PostMapping("/Playlsit/addVideo/{idPlaylist}/{listCode}")
     public Playlist addVideo(@PathVariable long idPlaylist,@PathVariable String[] listCode) {
        
        Playlist playlist = playlistRepository.findById(idPlaylist);
        Video addVideo = new Video();

            for (String code : listCode) {
                addVideo = videoRepository.findByCode(code);
                playlist.getListVideo().add(addVideo);
            }

         return  playlistRepository.save(playlist); // บันทึก Objcet ชื่อ Playlist

        }      
     }