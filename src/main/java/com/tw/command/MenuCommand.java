package com.tw.command;

import com.tw.command.Command;

public class MenuCommand implements Command {

    @Override
    public void invoke() {
        System.out.println("1. 添加学生\n" +
                "2. 生成成绩单\n" +
                "3. 退出\n" +
                "请输入你的选择（1～3）：\n");
    }
}
