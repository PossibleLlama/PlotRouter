import Header from '@/components/navbar/Header.vue';
import { mount } from '@vue/test-utils';
import { expect } from 'chai';

describe('Component has elements', () => {
  it('Component has a header', async () => {
    const wrapper = mount(Header);

    expect(wrapper.contains('header')).to.equal(true);
  });

  it('Header has navigation bar', async () => {
    const wrapper = mount(Header);

    expect(wrapper.contains('nav')).to.equal(true);
    expect(wrapper.contains('header > nav')).to.equal(true);
  });

  it('Header has link to home', async () => {
    const wrapper = mount(Header);

    expect(wrapper.contains('a')).to.equal(true);
    expect(wrapper.contains('a > h1')).to.equal(true);
    expect(wrapper.find('a > h1').text()).to.equal('PlotRouter');
  });
});

describe('Headers events emitted and received', () => {
  it('Toggle session emits clicked', async () => {
    const testUsername = 'myTestA';
    const wrapper = mount(Header);

    wrapper.vm.$emit('clicked', {
      username: testUsername,
      loggedIn: true,
    });

    // No idea why this has nested arrays
    expect(wrapper.emitted('clicked')[0][0].username).to.equal(testUsername);
    expect(wrapper.emitted('clicked')[0][0].loggedIn).to.equal(true);
  });
});
