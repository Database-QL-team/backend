package ggyuel.ggyuup.db2024Students.controller;


import ggyuel.ggyuup.db2024Students.StudentRank;
import ggyuel.ggyuup.db2024Students.dto.StudentRankRequestDTO;
import ggyuel.ggyuup.global.apiResponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;


@RestController
@RequiredArgsConstructor
@RequestMapping("/studentRank")
public class StudentRankController {


    @GetMapping("")
    public ApiResponse<ArrayList<StudentRankRequestDTO.StudentRankDTO>> getStudentRank() {
        return ApiResponse.onSuccess(StudentRank.getStudentsOrderedByRank());
    }
}
