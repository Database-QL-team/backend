package ggyuel.ggyuup.db2024Main.controller;

import com.sun.tools.javac.Main;
import ggyuel.ggyuup.db2024Main.dto.MainRequestDTO;
import ggyuel.ggyuup.db2024Main.dto.MainResponseDTO;
import ggyuel.ggyuup.db2024Main.service.MainPage;
import ggyuel.ggyuup.db2024Main.service.TodayPSDib;
import ggyuel.ggyuup.global.apiResponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    @PostMapping("/auth")
    public ApiResponse<String> postTodayPS () {
        String result = MainPage.postTodayPS();
        return ApiResponse.onSuccess(result);
    }

    @GetMapping("")
    public ApiResponse<MainResponseDTO.MainPageDTO> getMainPage() {
        MainResponseDTO.MainPageDTO MainPageInfo = MainPage.getMainPage();
        return ApiResponse.onSuccess(MainPageInfo);
    }

    @PutMapping("/todayps/dib/enable")
    public ApiResponse<String> enableTodayPSDib(@RequestBody MainRequestDTO.TodayPSDibInfoDTO dib) {
        String result = TodayPSDib.putTodayPSpicked(dib);
        return ApiResponse.onSuccess(result);
    }

}
