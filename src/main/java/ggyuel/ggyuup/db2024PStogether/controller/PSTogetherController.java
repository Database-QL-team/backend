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

/**
 * PSTogether 관련 요청을 처리하는 컨트롤러 클래스입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/pstogether")
public class PSTogetherController {

    /**
     * PSTogether를 저장합니다.
     *
     * @param request PSTogether를 저장하기 위한 요청 데이터
     * @return 성공 상태와 함께 저장된 PSTogether의 세부 정보를 포함하는 ApiResponse 객체
     */
    @PostMapping("")
    public ApiResponse<PSTogetherResponseDTO.PSTogetherDetailDTO> PSTogetherSave(@RequestBody @Validated PSTogetherRequestDTO.PSTogetherSaveDTO request) {
        PSTogetherResponseDTO.PSTogetherDetailDTO PSTogetherDTO = psTogetherSave(request);
        return ApiResponse.onSuccess(PSTogetherDTO);
    }

    /**
     * ID에 해당하는 PSTogether의 세부 정보를 반환합니다.
     *
     * @param id 게시글의 ID
     * @return 성공 상태와 함께 게시글의 세부 정보를 포함하는 ApiResponse 객체
     */
    @GetMapping("")
    public ApiResponse<PSTogetherResponseDTO.PSTogetherDetailDTO> getPSTogetherDetail(@RequestParam("id") int id) {
        PSTogetherResponseDTO.PSTogetherDetailDTO PSTogetherDTO = PSTogetherGetDetail.psTogetherGetDetail(id);
        return ApiResponse.onSuccess(PSTogetherDTO);
    }

    /**
     * 모든 PSTogether의 미리보기 목록을 반환합니다.
     *
     * @return 성공 상태와 함께 PSTogether 미리보기 목록을 포함하는 ApiResponse 객체
     */
    @GetMapping("/previews")
    public ApiResponse<ArrayList<PSTogetherResponseDTO.PSTogetherPreviewDTO>> getPSTogetherPreview() {
        ArrayList<PSTogetherResponseDTO.PSTogetherPreviewDTO> PSTogetherPreviewDTO = PSTogetherGetPreview.getPSTogetherPreview();
        return ApiResponse.onSuccess(PSTogetherPreviewDTO);
    }

    /**
     * 문제 번호에 따라 검색된 PSTogether의 미리보기 목록을 반환합니다.
     *
     * @param pid 문제 번호
     * @return 성공 상태와 함께 검색된 PSTogether 미리보기 목록을 포함하는 ApiResponse 객체
     */
    @GetMapping("/search")
    public ApiResponse<ArrayList<PSTogetherResponseDTO.PSTogetherPreviewDTO>> getPSTogetherSearch(@RequestParam("pid") int pid) {
        ArrayList<PSTogetherResponseDTO.PSTogetherPreviewDTO> PSTogetherPreviewDTO = PSTogetherSearch.getPSTogetherSearch(pid);
        return ApiResponse.onSuccess(PSTogetherPreviewDTO);
    }

    /**
     * PSTogether를 삭제합니다.
     *
     * @param delete 삭제 요청 데이터
     * @return 성공 상태와 함께 삭제 결과 메시지를 포함하는 ApiResponse 객체
     */
    @DeleteMapping("/delete")
    public ApiResponse<String> deletePSTogether(@RequestBody PSTogetherRequestDTO.PSTogetherDeleteDTO delete) {
        String result = null;
        Boolean isRemoved = PSTogetherDelete.PSTogethercheckPW(delete);
        if (isRemoved) {
            result = PSTogetherDelete.PSTogetherDelete(delete);
        }
        return ApiResponse.onSuccess(result);
    }
}

