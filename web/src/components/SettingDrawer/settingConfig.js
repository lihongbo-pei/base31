import themeColor from './themeColor.js'

const colorList = [
  {
    key: '薄暮', color: '#F5222D'
  },
  {
    key: '火山', color: '#FA541C'
  },
  {
    key: '日暮', color: '#FAAD14'
  },
  {
    key: '明青', color: '#13C2C2'
  },
  {
    key: '极光绿', color: '#52C41A'
  },
  {
    key: '拂晓蓝（默认）', color: '#1890FF'
  },
  {
    key: '极客蓝', color: '#2F54EB'
  },
  {
    key: '酱紫', color: '#722ED1'
  }
]

const updateTheme = newPrimaryColor => {
  themeColor.changeColor(newPrimaryColor).finally(() => {
  })
}

const updateColorWeak = colorWeak => {
  const app = document.body.querySelector('#app')
  colorWeak ? app.classList.add('colorWeak') : app.classList.remove('colorWeak')
}

export { updateTheme, colorList, updateColorWeak }
