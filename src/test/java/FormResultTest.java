import com.easipass.EpUtilServer.Main;
import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.entity.DTO.UploadMoreDTO;
import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.enumeration.ResponseFlagEnum;
import com.easipass.EpUtilServer.service.FormResultService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class FormResultTest {

    @Resource
    @Qualifier("TongXunFormResultServiceImpl")
    private FormResultService tongXunFormResultService;

    @Resource
    @Qualifier("YeWuFormResultServiceImpl")
    private FormResultService yeWuFormResultService;

    @Resource
    @Qualifier("BaseFormResultServiceImpl")
    private FormResultService baseFormResultService;

    /**
     * 通讯回执上传
     * */
    @Test
    public void uploadTongXun() {
        Response response = tongXunFormResultService.upload(
                "EDI208000001745771",
                new ResultDTO("0", "备注"),
                false,
                null,
                null
        );

        Assert.assertEquals(response.getFlag(), ResponseFlagEnum.TRUE.getFlag());
    }

    /**
     * 业务回执上传
     * */
    @Test
    public void uploadYeWu() {
        Response response = yeWuFormResultService.upload(
                "EDI208000001745771",
                new ResultDTO("0", "备注"),
                false,
                null,
                null
        );

        Assert.assertEquals(response.getFlag(), ResponseFlagEnum.TRUE.getFlag());
    }

    /**
     * 一次性上传
     * */
    @Test
    public void disposableUpload() {
        ResultDTO resultDTO = new ResultDTO("S", "备注");

        Response response = baseFormResultService.disposableUpload("EDI208000001745771", resultDTO);

        Assert.assertEquals(response.getFlag(), ResponseFlagEnum.TRUE.getFlag());
    }

    /**
     * 多份报关单上传回执
     * */
    @Test
    public void uploadMore() {
        List<UploadMoreDTO> uploadMoreDTOS = new ArrayList<>();
        uploadMoreDTOS.add(new UploadMoreDTO("EDI208000001745769", "S", "备注"));
        uploadMoreDTOS.add(new UploadMoreDTO("EDI208000001745770", "S", "备注"));
        uploadMoreDTOS.add(new UploadMoreDTO("EDI208000001745771", "S", "备注"));

        Response response = baseFormResultService.uploadMore(uploadMoreDTOS);

        Assert.assertEquals(response.getFlag(), ResponseFlagEnum.TRUE.getFlag());
    }

}
