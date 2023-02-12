package toyproject.almigty.lotto.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import toyproject.almigty.config.ControllerDocumentation;
import toyproject.almigty.lotto.application.LottoService;
import toyproject.almigty.lotto.rest.DTO.GetLuckyNumRspDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lotto/")
@ControllerDocumentation
@Slf4j
public class LottoController {

    private final LottoService lottoService;

    @GetMapping("get-lucky-number")
    public ResponseEntity<Object> getLotto(@RequestParam int count){
        log.info("getLuckyNumber{}",count);
        return new ResponseEntity(GetLuckyNumRspDTO.builder().luckyNumber(lottoService.createLottoNumber(count).get(0)).build(), HttpStatus.OK);
    }

    @GetMapping("win-percentage")
    public ResponseEntity<Object> getWinPercentage(@RequestParam int drwNo) throws ParseException {
        return new ResponseEntity(lottoService.getWinPercentage(drwNo), HttpStatus.OK);
    }
}
