package com.work.ppm.application.services.rdmanager;

import com.work.ppm.application.model.rdmanager.HrProject;

import java.util.List;

public interface IHrNewRequiresService {
    Integer keep(List<HrProject> hrProjects);
}
