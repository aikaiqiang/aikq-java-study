package com.space.aikq.game;

import lombok.extern.slf4j.Slf4j;

/**
 *  猴子分香蕉
 * @author aikaiqiang
 * @date 2019年06月28日 10:19
 */
@Slf4j
public class MonkeyShareBanana {

	public static void main(String[] args) {
		//猜想的次数
		int times = 1;

		retry:

		for (;;){
			int initNumber = getTheNumberOfRemainingBanana(times++);

			int  number = initNumber ;
			//5只猴子
			for(int i=0;i<5;i++){

				//获取上一只猴子 扔掉一根香蕉，平分五份带走一份后剩余的香蕉数
				int previous = getPreviousMonkeysBananaNumber(number);

				// i== 4 说明求第一只猴子的香蕉数，不管能否被4整除了
				if(previous % 4 == 0 || i == 4){
					number = previous;
				}else{
					log.error("猜想最终剩余香蕉数{}不满足:第{}只猴子拿走一份后剩{}只香蕉不能被4整除",initNumber,4-i,previous);
					continue retry;
				}
			}
			log.info("猜想最终剩余香蕉数{}满足:香蕉总数为{}, 遍历次数={}",initNumber,number, times);
			break ;
		}

		/**
		 * 递归写法
		 */
  /* int theFirstOnesBananas = getTheFirstOnesBananas(1, 0, 5);
   log.info("香蕉总数：{}",theFirstOnesBananas);*/

	}


	//获取上一只猴子 扔掉一根香蕉，平分五份带走一份后剩余的香蕉数
	public static int getPreviousMonkeysBananaNumber(int number){
		return number / 4 * 5 + 1;
	}

	//最后剩余的根数是4的倍数
	public static int getTheNumberOfRemainingBanana(int times){
		return 4 * times;
	}


	public static int getTheFirstOnesBananas(int times,int number,int count){
		int initNumber = 4 * times;
		if(number == 0){
			number = initNumber;
		}

		number = number / 4 * 5 + 1;

		if(count == 1){
			return number;
		}

		if(number % 4 == 0){
			return getTheFirstOnesBananas(times,number,--count);
		}

		return getTheFirstOnesBananas(++times,0,5);
	}
}
