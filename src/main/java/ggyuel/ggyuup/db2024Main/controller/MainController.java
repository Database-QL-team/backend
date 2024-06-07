package ggyuel.ggyuup.db2024Main.controller;

import ggyuel.ggyuup.db2024Main.dto.MainRequestDTO;
import ggyuel.ggyuup.db2024Main.dto.MainResponseDTO;
import ggyuel.ggyuup.db2024Main.service.MainPage;
import ggyuel.ggyuup.db2024Main.service.TodayPSDib;
import ggyuel.ggyuup.global.apiResponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


//
@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    /**
     * 메인 페이지 정보를 가져오는 엔드포인트
     *
     * @return ApiResponse<MainResponseDTO.MainPageDTO> 메인 페이지 정보가 포함된 성공 응답
     */
    @GetMapping("")
    public ApiResponse<MainResponseDTO.MainPageDTO> getMainPage() {
        // 메인 페이지 정보 가져오기
        MainResponseDTO.MainPageDTO MainPageInfo = MainPage.getMainPage();
        // 성공 응답 반환
        return ApiResponse.onSuccess(MainPageInfo);
    }

    /**
     * 오늘의 문제 찜 기능을 활성화하는 엔드포인트
     *
     * @param dib MainRequestDTO.TodayPSDibInfoDTO 오늘의 문제 찜 정보
     * @return ApiResponse<String> 성공 또는 실패 메시지 응답
     */
    @PutMapping("/todayps/dib/enable")
    public ApiResponse<String> enableTodayPSDib(@RequestBody MainRequestDTO.TodayPSDibInfoDTO dib) {
        // 오늘의 PS 찜 정보 업데이트
        String result = TodayPSDib.putTodayPSpicked(dib);
        // 응답 반환
        return ApiResponse.onSuccess(result);
    }

}

