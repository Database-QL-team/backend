package ggyuel.ggyuup.Students.controller;

import ggyuel.ggyuup.Students.service.StudentRank;
import ggyuel.ggyuup.Students.dto.StudentRankResponseDTO;
import ggyuel.ggyuup.global.apiResponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;

/**
 * StudentRankController 클래스는 학생 순위 관련 HTTP 요청을 처리합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/studentRank")
public class StudentRankController {

    /**
     * 학생 순위 정보를 가져와서 ApiResponse 형태로 반환합니다.
     *
     * @return ApiResponse<ArrayList<StudentRankResponseDTO.StudentRankDTO>> 형태의 학생 순위 정보
     */
    @GetMapping("")
    public ApiResponse<ArrayList<StudentRankResponseDTO.StudentRankDTO>> getStudentRank() {
        return ApiResponse.onSuccess(StudentRank.getStudentsOrderedByRank());
    }
}

