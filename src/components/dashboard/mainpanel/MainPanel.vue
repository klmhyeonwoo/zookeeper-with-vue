<script setup>
import { onMounted, onUnmounted, ref } from "vue";
import DataSection from "./DataSection.vue";

defineProps({
  cluster: String,
});

const scaledSingleNumber = (number) => {
  if (number < 10) return `0${number}`;
  return number;
};

let dateObj = new Date();

const dateRefObj = ref({
  year: dateObj.getFullYear(),
  month: scaledSingleNumber(dateObj.getMonth() + 1),
  day: scaledSingleNumber(dateObj.getDay()),
  hours: scaledSingleNumber(dateObj.getHours()),
  miniutes: scaledSingleNumber(dateObj.getMinutes()),
  seconds: scaledSingleNumber(dateObj.getSeconds()),
});

onMounted(() => {
  const timer = setInterval(() => {
    dateObj = new Date();

    dateRefObj.value.year = dateObj.getFullYear();
    dateRefObj.value.month = scaledSingleNumber(dateObj.getMonth() + 1);
    dateRefObj.value.day = scaledSingleNumber(dateObj.getDay());
    dateRefObj.value.hours = scaledSingleNumber(dateObj.getHours());
    dateRefObj.value.miniutes = scaledSingleNumber(dateObj.getMinutes());
    dateRefObj.value.seconds = scaledSingleNumber(dateObj.getSeconds());
  }, 1000);
});

onUnmounted(() => clearInterval(timer));
</script>

<template>
  <section :class="$style.section">
    <div :class="$style.top">
      <div :class="$style.title">Dash Board</div>
      <div :class="$style.timestamp">
        {{
          `${dateRefObj.year}.${dateRefObj.month}.${dateRefObj.day} ${dateRefObj.hours}:${dateRefObj.miniutes}:${dateRefObj.seconds}`
        }}
      </div>
      <div>
        클러스터 정보 및 기능 이용을 위해 좌측 패널에서 클러스터를 클릭해주세요
      </div>
    </div>
    <DataSection :cluster="cluster" v-if="cluster" />
  </section>
</template>

<style module>
.section {
  display: flex;
  flex-direction: column;
  padding: 4rem;
  row-gap: 2rem;
  width: 100%;
}

.top {
  display: flex;
  flex-direction: column;
  row-gap: 0.6rem;
}

.title {
  font-size: 34px;
  font-weight: 700;
}

.timestamp {
  font-size: 18px;
  font-weight: 300;
  color: #8b95a1;
  letter-spacing: 0.03rem;
}
</style>
