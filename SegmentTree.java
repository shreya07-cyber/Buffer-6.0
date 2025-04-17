package services;

public class SegmentTree {
    private final double[] tree;
    private final int n;

    public SegmentTree(int size) {
        this.n = size;
        this.tree = new double[2 * size];
    }

    public void update(int index, double value) {
        index += n;
        tree[index] += value;
        while (index > 1) {
            index /= 2;
            tree[index] = tree[2 * index] + tree[2 * index + 1];
        }
    }

    public double rangeSum(int left, int right) {
        double sum = 0;
        left += n;
        right += n;
        while (left < right) {
            if ((left & 1) == 1) sum += tree[left++];
            if ((right & 1) == 1) sum += tree[--right];
            left >>= 1;
            right >>= 1;
        }
        return sum;
    }
}
