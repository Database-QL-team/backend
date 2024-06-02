package ggyuel.ggyuup.db2024PStogether.controller;


import ggyuel.ggyuup.db2024PStogether.service.PSTogetherDelete;
import ggyuel.ggyuup.db2024PStogether.service.PSTogetherGetDetail;
import ggyuel.ggyuup.db2024PStogether.service.PSTogetherGetPreview;
import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherRequestDTO;
import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherResponseDTO;
import ggyuel.ggyuup.db2024PStogether.service.PSTogetherSearch;
import ggyuel.ggyuup.global.apiResponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static ggyuel.ggyuup.db2024PStogether.service.PSTogetherSave.psTogetherSave;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pstogether")
public class PSTogetherController {

    @PostMapping("")
    public ApiResponse<PSTogetherResponseDTO.PSTogetherDetailDTO> PSTogetherSave(@RequestBody @Validated PSTogetherRequestDTO.PSTogetherSaveDTO request) {
        PSTogetherResponseDTO.PSTogetherDetailDTO PSTogetherDTO = psTogetherSave(request);
        return ApiResponse.onSuccess(PSTogetherDTO);
    }

    @GetMapping("")
    public ApiResponse<PSTogetherResponseDTO.PSTogetherDetailDTO> getPSTogetherDetail(@RequestParam int id) {
        PSTogetherResponseDTO.PSTogetherDetailDTO PSTogetherDTO = PSTogetherGetDetail.psTogetherGetDetail(id);
        return ApiResponse.onSuccess(PSTogetherDTO);
    }

    @GetMapping("/previews")
    public ApiResponse<ArrayList<PSTogetherResponseDTO.PSTogetherPreviewDTO>> getPSTogetherPreview() {
        ArrayList<PSTogetherResponseDTO.PSTogetherPreviewDTO> PSTogetherPreviewDTO = PSTogetherGetPreview.getPSTogetherPreview();
        return ApiResponse.onSuccess(PSTogetherPreviewDTO);
    }

    @GetMapping("/search")
    public ApiResponse<ArrayList<PSTogetherResponseDTO.PSTogetherPreviewDTO>> getPSTogetherSearch(@RequestParam int pid) {
        ArrayList<PSTogetherResponseDTO.PSTogetherPreviewDTO> PSTogetherPreviewDTO = PSTogetherSearch.getPSTogetherSearch(pid);
        return ApiResponse.onSuccess(PSTogetherPreviewDTO);
    }

    @DeleteMapping("/delete")
    public ApiResponse<String> deletePSTogether(@RequestBody PSTogetherRequestDTO.PSTogetherDeleteDTO delete) {
        String result = null;
        Boolean isremoved = PSTogetherDelete.PSTogethercheckPW(delete);
        if (isremoved) {
            result = PSTogetherDelete.PSTogetherDelete(delete);
        }
        return ApiResponse.onSuccess(result);

    }

}
