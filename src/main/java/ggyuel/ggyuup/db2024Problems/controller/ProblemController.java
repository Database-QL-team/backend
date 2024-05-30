package ggyuel.ggyuup.db2024Problems.controller;


import ggyuel.ggyuup.db2024Problems.ProblemAlgo;
import ggyuel.ggyuup.db2024Problems.ProblemTier;
import ggyuel.ggyuup.db2024Problems.dto.ProblemRequestDTO;
import ggyuel.ggyuup.db2024Problems.dto.ProblemResponseDTO;
import ggyuel.ggyuup.db2024Students.StudentRank;
import ggyuel.ggyuup.db2024Students.dto.StudentRankRequestDTO;
import ggyuel.ggyuup.global.apiResponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problems")
public class ProblemController {

    @GetMapping("/algo")
    public ApiResponse<ArrayList<ProblemResponseDTO.ProblemAlgoDTO>> getProblemAlgo(@RequestBody @Validated ProblemRequestDTO.ProblemAlgoTag request) {
        return ApiResponse.onSuccess(ProblemAlgo.getProblemsByTag(request));
    }

    @GetMapping("/tier")
    public ApiResponse<ArrayList<ProblemResponseDTO.ProblemTierDTO>> getProblemTier(@RequestBody @Validated ProblemRequestDTO.ProblemTierTag request) {
        return ApiResponse.onSuccess(ProblemTier.getProblemsByTier(request));
    }
}
