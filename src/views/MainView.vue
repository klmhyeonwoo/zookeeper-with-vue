<script setup>
import SidePanel from "../components/dashboard/sidepanel/SidePanel.vue";
import MainPanel from "../components/dashboard/mainpanel/MainPanel.vue";
import { ref } from "vue";

const currentCluster = ref("");
const clusterContent = ref("");

const selectCluster = (name, address) => {
  currentCluster.value = name;
  clusterContent.value = address;
};
</script>

<template>
  <main>
    <!-- TODO : 레이아웃 배치 진행 -->
    <SidePanel :cluster="currentCluster" @select-cluster="selectCluster" />
    <MainPanel :cluster="currentCluster" />
  </main>
</template>

<style scoped>
main {
  display: flex;
}
</style>
