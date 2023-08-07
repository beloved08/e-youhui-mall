<template>
	<view class="countdown">
		<view class="countdown-title">{{title}}</view>
		<view class="countdown-timer">
			<view class="countdown-item">
				<view class="countdown-value">{{ days }}</view>
				<view class="countdown-label">天</view>
			</view>
			<view class="countdown-item">
				<view class="countdown-value">{{ hours }}</view>
				<view class="countdown-label">时</view>
			</view>
			<view class="countdown-item">
				<view class="countdown-value">{{ minutes }}</view>
				<view class="countdown-label">分</view>
			</view>
			<view class="countdown-item">
				<view class="countdown-value">{{ seconds }}</view>
				<view class="countdown-label">秒</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: "Countdown",
		props: {
			startDate: {
				type: String,
				required: true,
			},
			endDate: {
				type: String,
				required: true,
			},
		},
		data() {
			return {
				title: '',
				now: new Date(),
				intervalId: null,
				days: 0,
				hours: 0,
				minutes: 0,
				seconds: 0,
			};
		},
		methods: {
			updateTime(time,title) {
				this.title = title 
				//未开始
				const diff = Math.floor((new Date(time).getTime() - this.now.getTime()) / 1000);
				// if (diff <= 0) {
				// 	clearInterval(this.intervalId);
				// 	this.$emit("countdown-finished");
				// 	return;
				// }
				const remainingDays = Math.floor(diff / (24 * 60 * 60));
				const remainingHours = Math.floor((diff % (24 * 60 * 60)) / (60 * 60));
				const remainingMinutes = Math.floor((diff % (60 * 60)) / 60);
				const remainingSeconds = diff % 60;
				this.days = remainingDays < 10 ? "0" + remainingDays : remainingDays;
				this.hours = remainingHours < 10 ? "0" + remainingHours : remainingHours;
				this.minutes = remainingMinutes < 10 ? "0" + remainingMinutes : remainingMinutes;
				this.seconds = remainingSeconds < 10 ? "0" + remainingSeconds : remainingSeconds;
			},
		},
		mounted() {
			this.intervalId = setInterval(() => {
				this.now = new Date();
				if(new Date(this.startDate).getTime() - this.now.getTime() > 0){
					this.updateTime(this.startDate,'秒杀活动即将开始');
				}else{
					this.updateTime(this.endDate,'秒杀活动距离结束还剩');
				}
				
				if(Math.floor((new Date(this.endDate).getTime() - this.now.getTime() ) / 1000) <= 0){
					this.title = '秒杀活动已结束'
						clearInterval(this.intervalId);
						this.$emit("countdown-finished");
						return;
				}
			}, 1000);
		},
		destroyed() {
			clearInterval(this.intervalId);
		},
	};
</script>

<style scoped>
	.countdown {
		background-color: #333;
		color: #fff;
		padding: 1rem;
		border-radius: 0.5rem;
		text-align: center;
	}

	.countdown-title {
		font-size: 1.5rem;
		margin-bottom: 1rem;
	}

	.countdown-timer {
		display: flex;
		justify-content: space-around;
		align-items: center;
	}

	.countdown-item {
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.countdown-value {
		font-size: 3rem;
		font-weight: bold;
	}

	.countdown-label {
		font-size: 1rem;
	}
</style>
