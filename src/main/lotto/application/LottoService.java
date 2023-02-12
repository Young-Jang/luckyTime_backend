package toyproject.almigty.lotto.application;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import toyproject.almigty.lotto.domain.repository.LottoRepository;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LottoService {

    private final LottoRepository lottoRepository;

    public List<String> createLottoNumber(int count){
        List<String> lottoList = new ArrayList<>();
        List<Integer> lotto = new ArrayList<>();
        for(int j=0;j<count;j++) {
            lottoList.add(getLottoString(lotto));
            lotto.clear();
        }
        return lottoList;
    }

    private String getLottoString(List<Integer> lotto ){
        Set<Integer> checkSameNumber = new HashSet<>();
        for (int i = 0; i < 6; i++) {
            int luckyNumber = (int) ((Math.random() * 45) + 1);
            if (checkSameNumber.contains(luckyNumber)) {
                i--;
            }else{
                lotto.add(luckyNumber);
                checkSameNumber.add(luckyNumber);
            }
        }
        return buildPrettyString(lotto);
    }

    private String buildPrettyString(List<Integer> lotto){
        Collections.sort(lotto);
        return lotto.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("-","[","]"));
    }

    public String getWinPercentage(int drwNo) throws ParseException {
        String response = lottoRepository.getLottoNum(drwNo);
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(response);
        JSONObject jsonObject = (JSONObject) obj;
        StringBuilder luckyNumber = new StringBuilder();
        for(int i = 1; i < 7; i++) {
            luckyNumber.append(jsonObject.get("drwtNo"+i)).append(",");
        }
        System.out.println("[Lucky Number]: "+luckyNumber.toString());
        boolean endFlag = false;
        int count = 0;
        long maxCount = Long.parseLong(jsonObject.get("firstWinamnt").toString())/1000;
        System.out.println("[Lucky Number]: "+luckyNumber.toString() +"[maxNumber]: "+maxCount);

        while(!endFlag){
            List<String> lottoList = createLottoNumber(1);
            String result = lottoList.get(0).substring(1,lottoList.get(0).length()-1);
            String string = result.replaceAll("-","");
            if(string.equals(luckyNumber.toString()))
                endFlag = true;
            count++;
            if(count > maxCount)
                return "손해입니다.";
        }
        DecimalFormat format = new DecimalFormat("###,###");
        return "["+drwNo+"회차 당첨 번호]: "+luckyNumber.toString() +
                "\n - 프로그램 "+count+"번 실행시 당첨" +
                "\n - 당첨 금액 "+format.format(Long.parseLong(jsonObject.get("firstWinamnt").toString()))+"원"+
                "\n - 구매 금액 "+format.format(count * 1000)+"원";

   }
}
