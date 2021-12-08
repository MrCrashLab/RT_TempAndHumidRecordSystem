package ru.MrCrashLab.tempAndHumidRecordSystem.Backend.BackControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.MrCrashLab.tempAndHumidRecordSystem.Backend.Models.RecordTemperatureHumidityModel;
import ru.MrCrashLab.tempAndHumidRecordSystem.Backend.Services.RecordSystemService;

import java.util.List;

@RestController
@RequestMapping("/record-system")
public class APIController {
    @Autowired
    private RecordSystemService recordSystemService;

    @GetMapping("")
    public List<RecordTemperatureHumidityModel> getAllRecordList(){
        return recordSystemService.getAllRecordList();
    }
    @GetMapping("/{idController}")
    public List<RecordTemperatureHumidityModel> getRecordListByIdController(@PathVariable int idController){
        return recordSystemService.getRecordListByIdController(idController);
    }
}
