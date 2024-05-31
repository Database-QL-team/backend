package ggyuel.ggyuup.db2024PStogether.controller;


import ggyuel.ggyuup.db2024PStogether.PSTogetherSave;
import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherRequestDTO;
import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherResponseDTO;
import ggyuel.ggyuup.db2024Problems.ProblemAlgo;
import ggyuel.ggyuup.db2024Problems.dto.ProblemRequestDTO;
import ggyuel.ggyuup.db2024Problems.dto.ProblemResponseDTO;
import ggyuel.ggyuup.global.apiResponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static ggyuel.ggyuup.db2024PStogether.PSTogetherSave.psTogetherSave;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pstogether")
public class PSTogetherController {

    @PostMapping("")
    public ApiResponse<PSTogetherResponseDTO.PSTogetherDetailDTO> PSTogetherSave(@RequestBody @Validated PSTogetherRequestDTO.PSTogetherSaveDTO request) {
        PSTogetherResponseDTO.PSTogetherDetailDTO PSTogetherDTO = psTogetherSave(request);
        return ApiResponse.onSuccess(PSTogetherDTO);
    }

}
