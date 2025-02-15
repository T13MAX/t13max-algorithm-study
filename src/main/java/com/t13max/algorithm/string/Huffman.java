package com.t13max.algorithm.string;


import com.t13max.utils.BinaryStdIn;
import com.t13max.utils.BinaryStdOut;
import com.t13max.utils.MinPQ;

/**
 * 霍夫曼压缩
 * 只有叶子节点放字符 左0右1
 * (没测过)
 *
 * @Author 呆呆
 * @Datetime 2021/10/13 22:59
 */
public class Huffman {

    private static int R = 256;

    private static class Node implements Comparable<Node> {
        private char ch;
        private int freq;
        private final Node left, right;

        public Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }
    }

    public static void expand() {
        Node root = readTrie();
        int N = BinaryStdIn.readInt();
        for (int i = 0; i < N; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                if (BinaryStdIn.readBoolean()) {
                    x = x.right;
                } else {
                    x = x.left;
                }
            }
            BinaryStdOut.write(x.ch);
        }
        BinaryStdOut.close();
    }

    private static Node readTrie() {
        if (BinaryStdIn.readBoolean()) {
            return new Node(BinaryStdIn.readChar(), 0, null, null);
        }
        return new Node('\0', 0, readTrie(), readTrie());
    }

    private static String[] buildCode(Node root) {
        String[] st = new String[R];
        buildCode(st, root, "");
        return st;
    }

    private static void buildCode(String[] st, Node x, String s) {
        if (x.isLeaf()) {
            st[x.ch] = s;
            return;
        }//递归的01往上加 是叶子就把字符串的值给他
        buildCode(st, x.left, s + '0');
        buildCode(st, x.right, s + '1');
    }

    private static Node buildTrie(int[] freq) {
        MinPQ<Node> pq = new MinPQ<>();
        for (char c = 0; c < R; c++) {//把所有都当成叶子加入优先队列
            if (freq[c] > 0) {
                pq.insert(new Node(c, freq[c], null, null));
            }
        }
        while (pq.size() > 1) {//取最小的两个组合在一起 从最下面构造一棵树
            Node x = pq.delMin();
            Node y = pq.delMin();
            Node parent = new Node('\0', x.freq + y.freq, x, y);//新建一个俩孩子节点的父节点 没字符 只有俩孩子的权重和
            pq.insert(parent);
        }
        return pq.delMin();//最后只剩下一个 跳出了循环到这 把这个头结点返回
    }

    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    public static void compress() {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();
        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++) {
            freq[input[i]]++;
        }
        Node root = buildTrie(freq);
        String[] st = new String[R];
        buildCode(st, root, "");//构造编译表
        writeTrie(root);//输出单词查找树
        BinaryStdOut.write(input.length);
        for (int i = 0; i < input.length; i++) {
            String code = st[input[i]];//code就是字符对应的编码
            for (int j = 0; j < code.length(); j++) {//用truefalse来对应1 0
                if (code.charAt(j) == '1') {
                    BinaryStdOut.write(true);
                } else {
                    BinaryStdOut.write(false);
                }
            }
        }
        BinaryStdOut.close();
    }
}
