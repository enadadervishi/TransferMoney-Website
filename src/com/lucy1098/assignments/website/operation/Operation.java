package com.lucy1098.assignments.website.operation;

import com.lucy1098.assignments.website.auth.AuthManager;
import com.lucy1098.assignments.website.dao.Database;

@FunctionalInterface
public interface Operation {

    Result run(Database database, AuthManager auth) throws Exception;
}
