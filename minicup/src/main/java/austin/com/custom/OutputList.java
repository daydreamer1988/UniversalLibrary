package austin.com.custom;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * ArrayList查看原代码，发现以"["开头， 以"]"结尾， 中间以", "（逗号+空格）分割
 * <p>
 * 改写ArrayList<> toString方法，定制要输出的格式
 *
 */
class OutputList<E> extends ArrayList<E> {
    private final String start;
    private final String split;
    private final String end;

    /**
     * @param start 开始符号
     * @param split 分割符号
     * @param end 结束符号
     */
    public OutputList(String start, String split, String end) {
        this.start = start;
        this.split = split;
        this.end = end;
    }

    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (!it.hasNext())
            return "";

        StringBuilder sb = new StringBuilder();
        sb.append(start);
        for (; ; ) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!it.hasNext())
                return sb.append(end).toString();
            sb.append(split);
        }
    }
}