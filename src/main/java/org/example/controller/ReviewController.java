package org.example.controller;


import lombok.AllArgsConstructor;
import org.example.dto.ReviewGetAllResponseDTO;
import org.example.dto.ReviewResponseDTO;
import org.example.exception.*;
import org.example.manager.ReviewManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ReviewController {
  private ReviewManager manager;

  @RequestMapping("/review")
    public ReviewResponseDTO review (long productId, String review) throws ForbiddenException, NotAuthenticatedException, PasswordNotMatchesException, NotMakeReviewException {
      return manager.review(productId, review);
  }
  @RequestMapping("review.getAll")
  public List<ReviewGetAllResponseDTO> getAll (int limit, long offset) throws NotAuthenticatedException, PasswordNotMatchesException, NotMakeReviewException, InvalidDataException, ForbiddenException {
    return manager.getAll(limit, offset);
  }

}
