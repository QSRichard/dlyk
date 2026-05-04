package com.richard.web;


import com.github.pagehelper.PageInfo;
import com.richard.model.TClue;
import com.richard.result.R;
import com.richard.service.ClueService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ClueController {


    @Resource
    private ClueService clueService;


    @GetMapping(value = "/api/clues")
    public R cluePage(@RequestParam(value = "current", required = false) Integer current) {
        // required 为false 可以不传参数

        if (current == null) {
            current = 1;
        }
        PageInfo<TClue> userList = clueService.getClueByPage(current);
        return R.OK(userList);
    }

    @PostMapping(value = "/api/importExcel")
    public R importExcel(MultipartFile file) { // 参数名需要和前端formData中的字段名相同

        return R.OK(file);
    }
}
