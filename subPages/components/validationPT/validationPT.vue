<template>
	<view class="react" id="react">
		<view :style="{ width: width + 'px', backgroundColor: '#EDEFF2' }" class="left kong">{{ title }}</view>
		<view :style="{ left: width + 'px' }" @touchstart="start" @touchmove="move" @touchend="end" id="slider"
			:class="{ slider: true, select: title }">
			<image v-if="!title" src="https://eyouhui-1310105149.cos.ap-chengdu.myqcloud.com/wx/slider.png"
				mode="widthFix"></image>
			<!-- 这是两张图片一个是大于号一个是对号-->
			<image v-else src="https://eyouhui-1310105149.cos.ap-chengdu.myqcloud.com/wx/slider.png" mode="widthFix">
			</image>
		</view>
		<view class="right kong"> 滑动验证 </view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: '', //划过之后的标题
				width: 0, //滑到多宽
				reactWidth: 0, //整个矩形的宽度
				sliderWidth: 0, //滑块宽度
				startX: 0, //开始触摸距离屏幕左面的位置
				sendFlag: false, //是否发送
				finishFlag: false, //是否允许滑动 判断是否滑动完成
				moveFlag: false, //是否执行滑动函数
			};
		},
		mounted() {
			let selectFc = uni.createSelectorQuery().in(this);
			selectFc
				.select('#react')
				.boundingClientRect(data => {
					//获取总宽度
					this.reactWidth = data.width - 2; //矩形宽度去掉边框宽度
				})
				.exec();
			selectFc
				.select('#slider')
				.boundingClientRect(data => {
					//获取滑块的宽度
					this.sliderWidth = data.width;
				})
				.exec();
		},
		methods: {
			start(e) {
				//开始的触摸
				let {
					clientX,
					clientY
				} = e.touches[0];
				this.startX = clientX; //记录按下时刻距离屏幕左侧的距离
				this.moveFlag = true; //允许滑动
			},
			reset() {
				//重置划款状态
				this.sendFlag = false;
				this.finishFlag = false;
				this.width = 0;
				this.title = '';
			},
			finish() {
				//划款完成状态
				this.finishFlag = true;
				this.title = '验证通过';
			},
			move(e) {
				//划款移动中
				if (!this.moveFlag) return;
				if (this.width >= this.reactWidth - this.sliderWidth) {
					if (!this.sendFlag) {
						//到达最后面后就不允许他在滑动了，不然他会跳动体验比较差，所以加了限制
						this.moveFlag = false;
						this.sendFlag = true;
						this.$emit('validationPTChange', {
							finish: this.finish.bind(this),
							reset: this.reset.bind(this),
						}); //此时划款正好完成达到最右侧
					}
				} else {
					let {
						clientX,
						clientY
					} = e.touches[0];
					var width = clientX - this.startX; //下面判断要是小0就不能在滑动，要是大于最大长度也要停止
					if (width >= this.reactWidth - this.sliderWidth) {
						width = this.reactWidth - this.sliderWidth;
					} else if (width <= 0) {
						width = 0;
					}
					this.width = width;
				}
			},
			end(e) {
				//滑块结束时刻
				this.moveFlag = true;
				if (this.finishFlag) {
					//完成状态
					if (this.width < this.reactWidth - this.sliderWidth) {
						this.width = 0;
					}
				} else {
					//没有完成每次都要重置
					this.reset();
				}
			},
		},
	};
</script>

<style lang="scss" scoped>
	.react {
		margin: 0 auto;
		margin-top: 20rpx;
		width: 600rpx;
		height: 80rpx;
		box-sizing: content-box;
		background: #edeff2;
		border-radius: 30rpx;
		display: flex;
		align-items: center;
		position: relative;

		.sliderYuan {
			position: absolute;
			width: 100%;
			left: 0;
			top: 50%;
			margin: 0;
			transform: translate(0, -50%);
		}

		.slider {
			width: 90rpx;
			height: 128rpx;
			display: flex;
			align-items: center;
			justify-content: center;
			position: relative;

			image {
				display: block;
				width: 100rpx;
				height: auto;
			}
		}

		.kong {
			text-align: center;
			line-height: 80rpx;
			font-size: 26rpx;
			font-family: PingFangSC-Medium, PingFang SC;
			font-weight: 600;
			color: #353535;
			letter-spacing: 1rpx;

			&.right {
				flex: 1;
				color: #353535;
			}

			&.left {
				position: absolute;
				left: 1;
				top: 0;
				z-index: 10;
				height: 80rpx;
				color: #353535;
				border-radius: 50rpx;
			}
		}
	}
</style>
