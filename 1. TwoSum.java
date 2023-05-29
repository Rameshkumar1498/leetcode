class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] y=new int[2];
        for(int i=0;i<nums.length;i++){
            for(int j=0;j<nums.length;j++){
                if(i!=j)
                {
                    if(nums[i]+nums[j]==target)
                    {y[0]=i;
                    y[1]=j;
                    break;}
                }
            }
        }
        return y;
    }
}
