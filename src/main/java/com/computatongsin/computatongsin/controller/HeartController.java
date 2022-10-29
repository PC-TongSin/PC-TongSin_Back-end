package com.computatongsin.computatongsin.controller;

import com.computatongsin.computatongsin.dto.ResponseDto;
import com.computatongsin.computatongsin.security.MemberDetails;
import com.computatongsin.computatongsin.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/hearts/{board_id}")
    public ResponseDto<?> clickHeart(@PathVariable("board_id") Long boardId,
                                     @AuthenticationPrincipal MemberDetails memberDetails) {
        return heartService.heart(boardId, memberDetails.getMember());
    }
}