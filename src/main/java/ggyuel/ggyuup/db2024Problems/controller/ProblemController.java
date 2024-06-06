package ggyuel.ggyuup.db2024Problems.controller;


import ggyuel.ggyuup.db2024Problems.service.ProblemAlgo;
import ggyuel.ggyuup.db2024Problems.service.ProblemTier;
import ggyuel.ggyuup.db2024Problems.dto.ProblemResponseDTO;
import ggyuel.ggyuup.global.apiResponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problems")
public class ProblemController {

    @GetMapping("/algo")
    public ApiResponse<ArrayList<ProblemResponseDTO.ProblemAlgoDTO>> getProblemAlgo(@RequestParam String tag) {
        ArrayList<ProblemResponseDTO.ProblemAlgoDTO> ProblemAlgoList = ProblemAlgo.getProblemsByTag(tag);
        return ApiResponse.onSuccess(ProblemAlgoList);
    }

    @GetMapping("/tier")
    public ApiResponse<ArrayList<ProblemResponseDTO.ProblemTierDTO>> getProblemTier(@RequestParam int tier) {
        ArrayList<ProblemResponseDTO.ProblemTierDTO> ProblemTierList = ProblemTier.getProblemsByTier(tier);
        return ApiResponse.onSuccess(ProblemTierList);
    }
}
