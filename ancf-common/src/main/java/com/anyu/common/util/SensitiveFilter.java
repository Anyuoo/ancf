package com.anyu.common.util;

import com.anyu.common.exception.GlobalException;
import com.anyu.common.result.type.ResultType;
import org.apache.commons.lang3.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);
    private static final String REPLACEMENT = "***";
    private final TrieNode root = new TrieNode();

    /**
     * 读取铭感子文件，初始化前缀树
     */
    @PostConstruct
    private void init() {
        var filename = "sensitive-words.txt";
        final var stream = this.getClass().getClassLoader().getResourceAsStream(filename);
        if (stream == null) {
            logger.info("敏感字源文件:{} 无效", filename);
            return;
        }
        var reader = new BufferedReader(new InputStreamReader(stream));
        try (stream; reader) {
            String keyword;
            int num = 0;
            while ((keyword = reader.readLine()) != null) {
                if (this.saveKeywordToRoot(keyword)) num++;
            }
            logger.info("敏感字源文件初始化完成,共{}个", num);
        } catch (Exception e) {
            logger.error("初始化敏感字源出错");
            throw GlobalException.causeBy(ResultType.FAILED);
        }

    }

    /**
     * 添加敏感字
     */
    public boolean saveKeywordToRoot(String keyword) {
        if (keyword == null || keyword.length() < 1) {
            return false;
        }
        var temNode = root;
        char c;
        for (int idx = 0; idx < keyword.length(); idx++) {
            c = keyword.charAt(idx);
            if (temNode.getNextNode(c) == null) {
                TrieNode node = new TrieNode();
                temNode.setNextNode(c, node);
            }
            temNode = temNode.getNextNode(c);
        }
        //已存在
        if (temNode.isEnd()) return false;
        //敏感字结尾加上结束符
        temNode.setEnd(true);
        return true;
    }

    /**
     * 过滤字符串
     *
     * @param content 元字符串
     * @return 过滤后字符串
     */
    public String filter(String content) {
        final var result = new StringBuilder();
        final var length = content.length();
        TrieNode temNode;
        int end;
        char c, temC;
        for (var cur = 0; cur < length; cur++) {
            temNode = root;
            c = content.charAt(cur);
            //前缀树不存在字符，该字符为符号
            if (temNode.getNextNode(c) == null || isSymbol(c)) {
                result.append(c);
                continue;
            }
            temNode = temNode.getNextNode(c);
            end = cur;
            while (temNode != null) {
                //完整的敏感词
                if (temNode.isEnd()) break;
                temC = content.charAt(++end);
                //符号跳过
                if (isSymbol(temC)) continue;
                temNode = temNode.getNextNode(temC);
            }
            //处理敏感字，左臂右开
            if (temNode != null) {
                result.append(REPLACEMENT);
                cur = end;
                continue;
            }
            //非敏感词
            result.append(c);
        }
        return result.toString();
    }


    /**
     * 判断是否为符号
     */
    private boolean isSymbol(Character c) {
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    private static final class TrieNode {
        //下一个节点字符
        private final Map<Character, TrieNode> nextNode = new HashMap<>();
        //是否结束
        private boolean isEnd = false;

        public TrieNode getNextNode(Character key) {
            return key == null ? null : this.nextNode.get(key);
        }

        public void setNextNode(Character key, TrieNode node) {
            if (key == null || node == null) return;
            this.nextNode.put(key, node);
        }

        public boolean isEnd() {
            return isEnd;
        }

        public void setEnd(boolean end) {
            isEnd = end;
        }
    }

}

