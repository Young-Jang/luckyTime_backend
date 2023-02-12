package toyproject.almigty.lotto.application;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.json.simple.JSONObject;
import toyproject.almigty.lotto.application.LottoService;
import toyproject.almigty.lotto.infrastructure.LottoFeignClient;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class LottoServiceTest {
    @Autowired
    private LottoService lottoService;
    @Autowired
    private LottoFeignClient lottoFeignClient;

    private Set<String> set;

    @Test
    @DisplayName("로또회차별 당첨 확률")
    void dutchPayServiceTest() throws ParseException {
        int drwNo = 100;
        String response = lottoFeignClient.getLottoNum(drwNo);
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(response);
        JSONObject jsonObject = (JSONObject) obj;
        StringBuilder luckyNumber = new StringBuilder();
        for(int i = 1; i < 7; i++) {
            luckyNumber.append(jsonObject.get("drwtNo"+i));
        }
        System.out.println("[Lucky Number]: "+luckyNumber.toString());
        boolean endFlag = false;
        int count = 0;
        while(!endFlag){
            List<String> lottoList = lottoService.createLottoNumber(1);
            String result = lottoList.get(0).substring(1,lottoList.get(0).length()-1);
            String string = result.replaceAll("-","");
            if(string.equals(luckyNumber.toString()))
                endFlag = true;
            count++;
        }
        System.out.println(count);
    }

    @Test
    void forLoopTest() throws Exception{
        //given
        set = new HashSet<>();
        for(int i = 0 ;i<10000;i++)
            set.add("test"+i);
        set.add("test");
        //when
        String cardName = createName("test");

        //then
        System.out.println(cardName);
    }
    private boolean existsByname(String cardNickname){
        return set.contains(cardNickname);
    }

    private String createName(String cardName) throws UnsupportedEncodingException {
        String cardNickname = cardName;
        int nicknamePostFix = 0;

        while(existsByname(cardNickname)){
            String nextPostFix = Integer.toString(++nicknamePostFix);
            if(cardName.getBytes("euc-kr").length + nextPostFix.getBytes("euc-kr").length > 20){
                cardNickname = cardName.substring(0,20 - nextPostFix.getBytes("euc-kr").length) + nextPostFix;
            }else{
                cardNickname = cardName+nextPostFix;
            }
        }
        return cardNickname;
    }

    @Test
    public void ArrayThreadSafeTest(){
        //given
        List<String> list = new ArrayList<>();
        List<String> normalList = new ArrayList<>();
        List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>());

        for(int i = 0;i<10000;i++)
            list.add(String.valueOf(i));

        List<CompletableFuture<Void>> testFuture = list.stream()
                .map(num -> CompletableFuture.runAsync(() -> {
                    normalList.add(num);
                    synchronizedList.add(num);
                }))
                .collect(Collectors.toList());

        /* when */
        List<Void> testSync = CompletableFuture.allOf(testFuture.toArray(new CompletableFuture[0]))
                .thenApply(Void -> testFuture.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join();

        //then
        assertEquals(list.size(),synchronizedList.size());
        assertNotEquals(list.size(),normalList.size());
    }
}