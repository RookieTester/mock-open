package com.mock.web;

import com.mock.dto.BaseResult;
import com.mock.util.page.JsonResponse;
import com.mock.entity.MockInfo;
import com.mock.service.MockService;
import com.mock.util.MockUtil;
import com.mock.util.ResolveURL;
import com.mock.util.page.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.ws.rs.Produces;
import java.util.List;
import java.util.Map;
/**
 * Created by Administrator on 2017/1/9.
 */
@Controller
@RequestMapping(value = "mock")
public class MockController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockService mockService;

    @ResponseBody
    @Produces("application/json;charset=UTF-8")
    @RequestMapping(value = "execute")
    public BaseResult<Object> execute(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "alias", required = false) String alias,
            @RequestParam(value = "url", required = false) String url,
            @RequestParam(value = "json") String json,
            @RequestParam(value = "statusSwitch", required = false) Integer statusSwitch,
            @RequestParam(value = "statusValue", required = false) Integer statusValue) {
        if (id==null){//id为空执行插入操作
            //解析URL
            String proto = ResolveURL.read(url).get("proto");
            String domain = ResolveURL.read(url).get("domain");
            String context = ResolveURL.read(url).get("context");

            List<MockInfo> mockInfos = mockService.query();
            boolean checkResult = false;
            for (MockInfo mockInfo : mockInfos) {
                if (mockInfo.getUrl().equals(context)) {
                    return new BaseResult<Object>(false, "该接口已经存在mock数据，无法添加。若有需要，请直接修改该数据！");
                }
                if (mockInfo.getDomain().equals(domain)) {
                    checkResult = true;
                }
            }
            /**
             * 判断该域名是否已在Nginx server模块中配置，若未配置，则在Nginx的server模块自动添加一个server_name
             */
            if (checkResult == false) {
                MockUtil.updateDomain(domain);
            }

            //向JavaBean注入属性值
            MockInfo mockInfo = new MockInfo();
            mockInfo.setAlias(alias);
            mockInfo.setProto(proto);
            mockInfo.setDomain(domain);
            mockInfo.setUrl(context);
            mockInfo.setCompleteUrl(url);

            mockInfo.setJson(json);

            if (statusSwitch == 1) {
                mockInfo.setStatus(statusValue);
            } else if (statusSwitch == 0) {
                statusValue = 200;
                mockInfo.setStatus(200);
            }

            try {
                //向远程服务器发送命令,并获取moco端口号和文件名
                Map<String, String> map = MockUtil.configNginx(mockInfo.getUrl(), mockInfo.getJson(), mockInfo.getProto(), statusSwitch, statusValue);
                mockInfo.setFileName(map.get("fileName"));
                mockService.insert(mockInfo);
                return new BaseResult<Object>(true, "成功添加一条记录！");
            } catch (Exception e) {
                return new BaseResult<Object>(false, e.getMessage());
            }
        }else {//id不为空执行更新操作
            try {
                MockUtil.updateJson(mockService.queryById(id).getFileName(), json);
                MockInfo mockInfo = new MockInfo();
                mockInfo.setId(id);
                mockInfo.setJson(json);
                mockInfo.setAlias(alias);
                mockService.update(mockInfo);
                return new BaseResult<Object>(true, "更新成功");
            } catch (Exception e) {
                return new BaseResult<Object>(false, e.getMessage());
            }
        }
    }

    @RequestMapping(value = "insert")
    public ModelAndView insert(@RequestParam(value = "proto") String proto,
                               @RequestParam(value = "domain") String domain,
                               @RequestParam(value = "url") String url,
                               @RequestParam(value = "json") String json) {
        MockInfo mockInfo = new MockInfo();
        mockInfo.setProto(proto);
        mockInfo.setDomain(domain);
        mockInfo.setUrl(url);
        mockInfo.setJson(json);
        mockService.insert(mockInfo);
        return new ModelAndView("mock", null);
    }

    @ResponseBody
    @Produces("application/json;charset=UTF-8")
    @RequestMapping(value = "delete")
    public BaseResult<Object> delete(@RequestParam(value = "id") Integer id) {
        try {
            MockUtil.deleteMock(mockService.queryById(id).getProto(),mockService.queryById(id).getFileName());
            mockService.delete(id);
            return new BaseResult<Object>(true,"删除成功！");
        }catch (Exception e){
            return new BaseResult<Object>(false,e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "queryAll")
    public JsonResponse<MockInfo> queryAll(PageInfo pageInfo) {
        List<MockInfo> mockInfos = mockService.queryAll(pageInfo);
        JsonResponse<MockInfo> response = new JsonResponse<MockInfo>(mockInfos, pageInfo);
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "query")
    public List<MockInfo> query() {
        List<MockInfo> mockInfos = mockService.query();
        return mockInfos;
    }

    @ResponseBody
    @Produces("application/json;charset=UTF-8")
    @RequestMapping(value = "queryById")
    public MockInfo queryById(@RequestParam(value = "id") Integer id) {
        return this.mockService.queryById(id);
    }

    @ResponseBody
    @Produces("application/json;charset=UTF-8")
    @RequestMapping(value = "queryByAlias")
    public List<MockInfo> queryByAlias(PageInfo pageInfo, @RequestParam(value = "alias") String alias) {
        return this.mockService.queryByAlias(pageInfo, alias);
    }

    @RequestMapping(value = "queryByProto")
    public List<MockInfo> queryByProto(PageInfo pageInfo, @RequestParam(value = "proto") String proto) {
        List<MockInfo> mockInfos = mockService.queryByProto(pageInfo, proto);
        return mockInfos;
    }

    @RequestMapping(value = "queryByDomain")
    public List<MockInfo> queryByDomain(PageInfo pageInfo, @RequestParam(value = "domain") String domain) {
        List<MockInfo> mockInfos = mockService.queryByDomain(pageInfo, domain);
        return mockInfos;
    }

    @ResponseBody
    @RequestMapping(value = "queryByUrl")
    public JsonResponse<MockInfo> queryByUrl(@RequestParam(value = "url") String url, PageInfo pageInfo) {
        List<MockInfo> mockInfos = mockService.queryByUrl(pageInfo, url);
        JsonResponse<MockInfo> response = new JsonResponse<MockInfo>(mockInfos, pageInfo);
        return response;
    }

    @ResponseBody
    @Produces("application/json;charset=UTF-8")
    @RequestMapping(value = "edit")
    public BaseResult<Object> edit(@RequestParam(value = "id") Integer id, @RequestParam(value = "alias") String alias) {
        try {
            MockInfo mockInfo = new MockInfo();
            mockInfo.setId(id);
            mockInfo.setAlias(alias);
            mockInfo.setJson(mockService.queryById(id).getJson());
            mockService.update(mockInfo);
            return new BaseResult<Object>(true, "更新成功");
        } catch (Exception e) {
            return new BaseResult<Object>(false, e.getMessage());
        }
    }

}
