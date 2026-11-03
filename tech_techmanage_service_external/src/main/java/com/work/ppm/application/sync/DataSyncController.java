package com.work.ppm.application.sync;

import com.work.ppm.application.services.workflow.PlatformEditorService;
import com.tansun.workflow.api.AbstractWorkflowEditorService;
import com.tansun.workflow.model.EditorBean;
import com.tansun.workflow.model.EditorInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workflowEditorService")
@Api(value = "data-sync", tags = "工作流数据同步接口")
public class DataSyncController extends AbstractWorkflowEditorService {

    @Autowired
    private PlatformEditorService platformEditorService;

    /**
     * @param editorInfo
     * @return
     */
    @RequestMapping("/findUsersList")//固定写法，不需要更改
    @Override
    public EditorBean findUsersList(@RequestBody EditorInfo editorInfo) {
        return platformEditorService.findUsersList(editorInfo);
    }

    /**
     * @param editorInfo
     * @return
     */
    @RequestMapping("/findUserOrgRoleList")//固定写法，不需要更改
    @Override
    public EditorBean findUserOrgRoleList(@RequestBody EditorInfo editorInfo) {
        return platformEditorService.findUserOrgRoleList(editorInfo);
    }

    /**
     * @param editorInfo
     * @return
     */
    @RequestMapping("/findAllPostInfo")//固定写法，不需要更改
    @Override
    public EditorBean findAllPostInfo(@RequestBody EditorInfo editorInfo) {
        return platformEditorService.findAllPostInfo(editorInfo);
    }
}
