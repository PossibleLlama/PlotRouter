import Header from '@/components/navbar/Header.vue';
import { mount } from '@vue/test-utils';
import { expect } from 'chai';

describe('Component has elements', () => {
  it('Components has a header', async () => {
    const wrapper = mount(Header);

    expect(wrapper.contains('header')).to.equal(true);
  });

  it('Components has navigation bar', async () => {
    const wrapper = mount(Header);

    expect(wrapper.contains('nav')).to.equal(true);
  });
});
