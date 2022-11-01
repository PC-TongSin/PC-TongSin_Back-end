package com.computatongsin.computatongsin.controller;

import com.computatongsin.computatongsin.dto.ResponseDto;
import com.computatongsin.computatongsin.dto.req.MypageRequestDto;
import com.computatongsin.computatongsin.entity.Member;
import com.computatongsin.computatongsin.security.MemberDetails;
import com.computatongsin.computatongsin.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    // 마이 페이지
    
    // 내가 작성한 게시글 불러오기
    @GetMapping("/user-boards")
    public ResponseDto<?> getMyBoardList(@AuthenticationPrincipal MemberDetails memberDetails) {
        return myPageService.getMyBoardList(memberDetails.getMember());
    }

    // 내가 작성한 댓글 불러오기
    @GetMapping("/user-comments")
    public ResponseDto<?> getMyCommentList(@AuthenticationPrincipal MemberDetails memberDetails) {
        return myPageService.getMyCommentList(memberDetails.getMember());
    }

    // 내가 좋아요한 게시글 불러오기
    @GetMapping("/user-hearts")
    public ResponseDto<?> getMyHeartList(@AuthenticationPrincipal MemberDetails memberDetails) {
        return myPageService.getMyHeartList(memberDetails.getMember());
    }


    // 내가 작성한 게시글 불러오기 (페이저)
    @GetMapping("/userpagerboard")
    public ResponseDto<?> getMyBoardPagerList(@AuthenticationPrincipal MemberDetails memberDetails, @RequestParam("page") int page,
                                              @RequestParam("size") int size,
                                              @RequestParam("sortBy") String sortBy,
                                              @RequestParam("isAsc") boolean isAsc) {
        int pageTemp = page - 1;
        return myPageService.getMyBoardPagerList(pageTemp,size,sortBy,isAsc, memberDetails.getMember());
    }

    // 내가 작성한 댓글 불러오기 (페이저)
    @GetMapping("/userpagercomment")
    public ResponseDto<?> getMyCommentPagerList(@AuthenticationPrincipal MemberDetails memberDetails, @RequestParam("page") int page,
                                                @RequestParam("size") int size,
                                                @RequestParam("sortBy") String sortBy,
                                                @RequestParam("isAsc") boolean isAsc) {
        int pageTemp = page - 1;
        return myPageService.getMyCommentPagerList(pageTemp,size,sortBy,isAsc, memberDetails.getMember());
    }

    // 내가 좋아요한 게시글 불러오기 (페이저)
    @GetMapping("/userpagerheart")
    public ResponseDto<?> getMyHeartPagerList(@AuthenticationPrincipal MemberDetails memberDetails, @RequestParam("page") int page,
                                              @RequestParam("size") int size,
                                              @RequestParam("sortBy") String sortBy,
                                              @RequestParam("isAsc") boolean isAsc) {
        int pageTemp = page - 1;
        return myPageService.getMyHeartPagerList(pageTemp,size,sortBy,isAsc, memberDetails.getMember());
    }

    // 현재 유저 정보 보내기
    @GetMapping("/userinfo/username")
    public ResponseDto<?> userinfo(@AuthenticationPrincipal MemberDetails memberDetails) {
        String username = memberDetails.getMember().getUsername();
        return ResponseDto.success(username);
    }

    // 현재 유저 닉네임 정보 보내기
    @GetMapping("/userinfo/nickname")
    public ResponseDto<?> userinfoNickname(@AuthenticationPrincipal MemberDetails memberDetails) {
        String nickname = memberDetails.getMember().getNickname();
        return ResponseDto.success(nickname);
    }

    // 현재 유저의 닉네임 수정
    @PutMapping("/modified")
    public ResponseDto<?> userinfoModify(
            @RequestBody MypageRequestDto mypageRequestDto,
            @AuthenticationPrincipal MemberDetails memberDetails) {
        return myPageService.userinfoModify(mypageRequestDto, memberDetails.getMember());
    }
}
