<script setup>
  import SidePanel from "../components/dashboard/sidepanel/SidePanel.vue";
  import MainPanel from "../components/dashboard/mainpanel/MainPanel.vue";
  import { ref } from "vue";

  const currentCluster = ref("");
  const clusterContent = ref("");
  const clusterAddress = ref("");

  const selectCluster = (name, host) => {
    currentCluster.value = name;
    clusterContent.value = host;
  };

  const updateAddress = (address) => {
    clusterAddress.value = address;
  };
</script>

<template>
  <main>
    <SidePanel :cluster="currentCluster" @select-cluster="selectCluster" />
    <MainPanel
      :cluster="currentCluster"
      :address="clusterAddress"
      @update-address="updateAddress"
    />
  </main>
</template>

<style scoped>
  main {
    display: flex;
    height: 100%;
    width: 100vw;
  }
</style>
