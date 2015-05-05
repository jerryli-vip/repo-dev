package com.pactera.web.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pactera.web.common.Pagination;
import com.pactera.web.common.Token;
import com.pactera.web.exception.ServiceException;
import com.pactera.web.model.Department;
import com.pactera.web.service.DepartmentService;

@Controller
@RequestMapping("dept")
public class DepartmentController extends BaseController {

	Logger log = Logger.getLogger(DepartmentController.class);

	private final String paginationURL = "dept/list";

	@Autowired
	DepartmentService departmentService;

	@RequestMapping(value = "list")
	public ModelAndView list(String pageNo) throws ServiceException {
		final String METHOD_NAME = "list";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = new ModelAndView("deptList");

		Pagination pagination = new Pagination();
		pagination.setPageNo(StringUtils.isNumeric(pageNo) ? new Integer(pageNo) : new Integer(1));

		List<Department> deptList = departmentService.findAll(pagination);
		if (!CollectionUtils.isEmpty(deptList)) {
			mav.addObject("deptList", deptList);
			mav.addObject("pagination", pagination);
			mav.addObject("paginationURL", paginationURL);
		}

		log.debug(METHOD_NAME + " end");
		return mav;
	}

	@Token(save = true)
	@RequestMapping(value = "showCreate")
	public ModelAndView showCreate() throws ServiceException {
		final String METHOD_NAME = "showCreate";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = new ModelAndView("deptNew");

		log.debug(METHOD_NAME + " end");
		return mav;
	}

	@Token(remove = true)
	@RequestMapping(value = "create")
	public ModelAndView create(@RequestParam String deptName, String location) throws ServiceException {
		final String METHOD_NAME = "create";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = null;
		Department dept = new Department();
		dept.setDeptName(deptName);
		dept.setLocation(location);
		departmentService.save(dept);

		mav = list("0");

		log.debug(METHOD_NAME + " end");
		return mav;
	}

	@RequestMapping(value = "showEdit")
	public ModelAndView showEdit(@RequestParam Integer deptno) throws ServiceException {
		final String METHOD_NAME = "showEdit";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = new ModelAndView("deptEdit");

		Department dept = departmentService.findById(deptno);

		mav.addObject("dept", dept);

		log.debug(METHOD_NAME + " end");
		return mav;
	}

	@RequestMapping(value = "edit")
	public ModelAndView edit(Department dept) throws ServiceException {
		final String METHOD_NAME = "edit";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = null;

		departmentService.save(dept);

		mav = new ModelAndView("redirect:list");

		log.debug(METHOD_NAME + " end");
		return mav;
	}

	@RequestMapping(value = "delete")
	public ModelAndView delete(@RequestParam Integer deptno) throws ServiceException {
		final String METHOD_NAME = "delete";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = null;

		departmentService.delete(deptno);

		mav = new ModelAndView("redirect:list");

		log.debug(METHOD_NAME + " end");
		return mav;
	}
}
