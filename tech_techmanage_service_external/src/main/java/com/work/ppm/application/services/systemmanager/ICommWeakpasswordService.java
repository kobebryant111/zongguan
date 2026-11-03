package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.model.systemmanager.WeakPassword;

import java.util.List;

public interface ICommWeakpasswordService {

    int addWeakPasswords(List<WeakPassword> weakPasswords);

}
