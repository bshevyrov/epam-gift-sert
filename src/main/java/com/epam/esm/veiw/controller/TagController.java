package com.epam.esm.veiw.controller;

import com.epam.esm.veiw.Error;
import com.epam.esm.veiw.dto.TagDTO;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.facade.TagFacade;
;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/tags")
public class TagController {

    private final TagFacade tagFacade;

    @Autowired
    public TagController(TagFacade tagFacade) {
        this.tagFacade = tagFacade;
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TagDTO> saveTagTDO(@RequestBody TagDTO tagDTO, UriComponentsBuilder ucb) {
        long tagId = tagFacade.create(tagDTO);

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/tags/")
                .path(String.valueOf(tagId))
                .build().toUri();

        headers.setLocation(locationUri);

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET)
    public TagDTO tagById(@PathVariable long id) {
        return tagFacade.findById(id);
    }
    @RequestMapping(value = "",
            method = RequestMethod.GET)
    public List<TagDTO> findAll() {
        return tagFacade.findAll();
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<TagDTO> deleteById( @PathVariable long id) {
        tagFacade.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH)
    public void updateTag(@RequestBody Map<String,Object> updates,
                          @PathVariable long id) {
        tagFacade.update(updates);
    }
  @ExceptionHandler(TagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error tagNotFound(TagNotFoundException e) {
        long tagId = e.getTagId();
        return new Error(4, "Tag [" + tagId + "] not found");
    }
}