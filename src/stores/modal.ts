import { useApiRegisterCluster } from "../hooks/api/sidepanel/useApiRegisterCluster";
import { defineStore, storeToRefs } from "pinia";
import { reactive, ref, toRef, toRefs } from "vue";
import { api } from "../api";
import useToastStore from "../stores/toast";
import useGlobalState from "../stores/state";
import { registerNode } from "../hooks/api/mainpanel/useApiRegisterNode";
import { getClusterTree } from "../hooks/api/mainpanel/useApiGetClusterTree";
import { getMetaData } from "../hooks/api/mainpanel/useApiGetNodeMeta";

export const useModalStore = defineStore("modal", () => {
  const doubleInputModalState = ref(false);
  const warningModalState = ref(false);

  const toastStore = useToastStore();
  const { openToast } = toastStore;

  const globalStore = useGlobalState();
  const { clusterTreeData, clusterMetaData } = storeToRefs(globalStore);

  const modalInfo = ref({
    firstValue: "",
    firstLabel: "",
    firstPlaceholder: "",
    twiceValue: "",
    twiceLabel: "",
    twicePlaceholder: "",
    clusterAddNodeAddress: "",
    clusterAddNodeValue: "",
    clusterCurNodeValue: "",
    clusterName: "",
    confirm: () => {},
  });

  const registerCluster = useApiRegisterCluster();

  const openModal = (feature: string) => {
    switch (feature) {
      case "addCluster":
        (modalInfo.value.firstValue = ""),
          (modalInfo.value.firstLabel = "클러스터 주소"),
          (modalInfo.value.firstPlaceholder = "클러스터 주소를 입력해주세요"),
          (modalInfo.value.twiceValue = ""),
          (modalInfo.value.twiceLabel = "클러스터 이름"),
          (modalInfo.value.twicePlaceholder = "클러스터 이름을 입력해주세요");
        modalInfo.value.confirm = () => {
          registerCluster.mutate({
            address: modalInfo.value.firstValue,
            name: modalInfo.value.twiceValue,
          });
        };
        doubleInputModalState.value = true;
        break;
      case "addNodeTree":
        (modalInfo.value.firstValue = ""),
          (modalInfo.value.firstLabel = "노드 경로"),
          (modalInfo.value.firstPlaceholder = "노드 경로를 입력해주세요"),
          (modalInfo.value.twiceValue = ""),
          (modalInfo.value.twiceLabel = "노드 값"),
          (modalInfo.value.twicePlaceholder =
            "노드에 추가하고자 하는 값을 입력해주세요");
        doubleInputModalState.value = true;
        break;
      case "duplicateNodeData":
        (modalInfo.value.firstValue = ""),
          (modalInfo.value.twiceValue = ""),
          (modalInfo.value.confirm = () => {
            registerNode(
              modalInfo.value.clusterName,
              modalInfo.value.clusterAddNodeValue,
              modalInfo.value.clusterAddNodeAddress,
              true,
            )
              .then(() => {
                getClusterTree(modalInfo.value.clusterName)
                  .then((res) => {
                    openToast(true);
                    clusterTreeData.value = toRef(res)
                    closeModal();
                  })
                  .catch(() => {
                    openToast(false);
                    closeModal();
                  });
                getMetaData(modalInfo.value.clusterName)
                  .then((res) => {
                    openToast(true);
                    clusterMetaData.value = toRef(res);
                    closeModal();
                  })
                  .catch(() => {
                    openToast(false);
                    closeModal();
                  });
              })
              .catch(() => {
                openToast(false);
                closeModal();
              });
          });
        warningModalState.value = true;
        break;
      default:
        break;
    }
    document.body.style.overflow = "hidden";
  };

  const closeModal = () => {
    const checkerOpenModal = () => {
      const checkerList = [doubleInputModalState, warningModalState];
      checkerList.forEach((item) => {
        item.value = false;
      });
    };
    if (modalInfo.value.firstValue || modalInfo.value.twiceValue) {
      if (
        window.confirm(
          `변경 사항이 저장되지 않을 수 있습니다, 정말 떠나시겠습니까?`,
        )
      ) {
        checkerOpenModal();
        document.body.style.overflow = "auto";
      }
    } else {
      checkerOpenModal();
      document.body.style.overflow = "auto";
    }
  };

  return {
    doubleInputModalState,
    warningModalState,
    openModal,
    closeModal,
    confirm,
    modalInfo,
  };
});

export default useModalStore;
