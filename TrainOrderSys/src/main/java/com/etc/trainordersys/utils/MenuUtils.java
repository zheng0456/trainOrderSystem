package com.etc.trainordersys.utils;
import com.etc.trainordersys.entity.MenuEntity;

import java.util.ArrayList;
import java.util.List;

//菜单工具类
public class MenuUtils {
    //获取一级菜单列表，父菜单对象为null
    public static List<MenuEntity> getTopMenus(List<MenuEntity> lists){
        List<MenuEntity> topMenus=new ArrayList<>();//存放一级菜单的集合
        for (MenuEntity menu:lists){  //遍历所有菜单列表
            if (menu.getParent()==null){  //判断当前对象的父菜单对象是否为null ，是null---一级菜单
                topMenus.add(menu);  //把当前菜单添加到存放一级菜单的集合中
            }
        }
        return topMenus;
    }

    //获取二级菜单列表，父菜单对象不为null，父菜单对象的父菜单为null
    public static List<MenuEntity> getSecondMenus(List<MenuEntity> lists){
        List<MenuEntity> secondMenus=new ArrayList<>(); //存放二级菜单的集合
        for (MenuEntity menu:lists){  //遍历所有菜单列表
            if (menu.getParent()!=null && menu.getParent().getParent()==null){  //判断当前对象的父菜单不为null，父菜单对象的父菜单为null
                secondMenus.add(menu);   //把当前菜单添加到存放二级菜单的集合中
            }
        }
        return secondMenus;
    }

    //获取二级菜单列表，父菜单对象不为null，父菜单对象的父菜单不为null
    public static List<MenuEntity> getThirdMenus(List<MenuEntity> lists){
        List<MenuEntity> thirdMenus=new ArrayList<>();  //存放三级菜单的集合
        for (MenuEntity menu:lists){ //遍历所有菜单列表
            if (menu.getParent()!=null && menu.getParent().getParent()!=null){  //判断当前对象的父菜单对象不为null，父菜单对象的父菜单不为null
                thirdMenus.add(menu);  //把当前菜单添加到存放三级菜单的集合中
            }
        }
        return thirdMenus;
    }
    //根据菜单的url获取菜单id
    public static Integer getMenuIdByUrl(String url, List<MenuEntity> userSecondMenus) {
        //如果url为null,返回id为0
        if (url != null){
            //遍历二级菜单列表
            for (MenuEntity secondMenu : userSecondMenus){
                //找到包含url菜单的对象
                if (secondMenu.getUrl().equals(url)){
                    return secondMenu.getMenu_id();
                }
            }
        }
        return 0;
    }
    //获取某个菜单的id所有子分类（三级菜单）  menuId:二级菜单id  menuList:当前登录的用户可操作的所有菜单列表
    public static List<MenuEntity> getChild(Integer menuId, List<MenuEntity> menuList) {
        List<MenuEntity>  child = new ArrayList<>();//存放某个二级菜单的三级菜单列表
        if (menuId !=0){
            //遍历所有菜单列表
            for (MenuEntity menu : menuList){
                //通过二级菜单的id查询所有子菜单列表
                if (menu.getParent_id() == menuId){
                    child.add(menu);
                }
            }
        }
        return child;
    }

}
