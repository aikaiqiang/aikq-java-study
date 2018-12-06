package com.space.aikq.offer;

/**
 *  剑指offer 二维数组中查找
 * @author aikq
 * @date 2018年12月06日 14:06
 */
public class ArrayTest {

	public boolean Find(int target, int [][] array) {
		int a = array.length;
		boolean result = false;
		for (int i = 0; i < a; i ++){
			int[] tempArray = array[i];
			if(tempArray.length == 0){
				break;
			}
			int left = 0;
			int right = array.length -1;
			result = binaryFind(tempArray, left, right, target);
			if (result){
				break;
			}
		}
		return result;
	}

	public boolean binaryFind(int[] array, int left, int right, int value){
		if (left > right){
			return false;
		}
		if (array[left] == value || array[right] == value){
			return true;
		}

		int middle = (left + right)/2;
		if (array[middle] == value){
			return true;
		}
		if (array[middle] > value){
			left = left + 1;
			right = middle -1;
			return binaryFind(array, left, right, value);
		}
		if (array[middle] < value){
			left = middle + 1;
			right = right -1;
			return binaryFind(array, left, right, value);
		}
		return false;
	}

	public boolean Find2(int target,int [][] array) {
		int row=0;
		int col=array[0].length-1;
		while(row<=array.length-1&&col>=0){
			if(target==array[row][col]){
				return true;
			} else if(target>array[row][col]) {
				row++;
			} else{
				col--;
			}
		}
		return false;

	}

	public static void main(String[] args) {

		int [][] array = new int[][]{{},{}, {},{}};
		ArrayTest arrayTest = new ArrayTest();
		boolean result = arrayTest.Find2(7, array);
		if (result){
			System.out.println("存在");
		}else {
			System.out.println("不存在");
		}
	}

}
