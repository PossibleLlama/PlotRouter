import Footer from '@/components/Footer.vue';
import { mount } from '@vue/test-utils';
import { expect } from 'chai';

describe('Component has elements', () => {
  it('Components has a footer', async () => {
    const wrapper = mount(Footer);

    expect(wrapper.contains('footer')).to.equal(true);
  });
});
